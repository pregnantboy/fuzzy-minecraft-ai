//
//  CreateAIViewController.m
//  FuzzyAIBuilder
//
//  Created by Ian Chen on 24/1/16.
//  Copyright © 2016 Ian Chen. All rights reserved.
//

#import "CreateAIViewController.h"
#import "MainMenuViewController.h"
#import "RulesListViewController.h"
#import "AIObject.h"
#import "AIDatabase.h"

@interface CreateAIViewController ()

@end

@implementation CreateAIViewController

- (void)viewDidLoad {
    [super viewDidLoad];
    [self.background setAlphaValue:0.90];
    [self.backButton setAction:@selector(goToMainPage)];
    [self.nextButton setAction:@selector(createNewAI)];
    [MainMenuViewController addShadow:self.backButton];
    [MainMenuViewController addShadow:self.nextButton];
}

- (IBAction)goToMainPage {
    NSStoryboard *mainsb = [NSStoryboard storyboardWithName:@"Main" bundle:[NSBundle mainBundle]];
    NSViewController *mainMenuVc = [mainsb instantiateControllerWithIdentifier:@"MainMenuViewController"];
    [[mainMenuVc view] setFrame:self.view.frame];
    [[NSApp mainWindow] setContentViewController:mainMenuVc];
}

- (IBAction)createNewAI {
    AIObject *newAI;
    if ([self.name.stringValue length] > 0) {
         newAI = [[AIObject alloc] initWithAIName:self.name.stringValue];
    } else {
        return;
    }
    NSMutableArray *AIarray = [AIDatabase loadData];
    [AIarray addObject:newAI];
    NSStoryboard *mainsb = [NSStoryboard storyboardWithName:@"Main" bundle:[NSBundle mainBundle]];
    RulesListViewController *vc = (RulesListViewController *)[mainsb instantiateControllerWithIdentifier:@"RulesListViewController"];
    [[vc view] setFrame:self.view.frame];
    [[NSApp mainWindow] setContentViewController:vc];
    [vc loadAI:AIarray atIndex:[AIarray count] -1];
}


@end
