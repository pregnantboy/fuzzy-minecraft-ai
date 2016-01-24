//
//  CreateAIViewController.m
//  FuzzyAIBuilder
//
//  Created by Ian Chen on 24/1/16.
//  Copyright © 2016 Ian Chen. All rights reserved.
//

#import "CreateAIViewController.h"
#import "MainMenuViewController.h"

@interface CreateAIViewController ()

@end

@implementation CreateAIViewController

- (void)viewDidLoad {
    [super viewDidLoad];
    [self.background setAlphaValue:0.90];
    [self.backButton setAction:@selector(goToCreatePage)];
    [MainMenuViewController addShadow:self.backButton];
    [MainMenuViewController addShadow:self.nextButton];
}

- (IBAction)goToCreatePage {
    NSStoryboard *mainsb = [NSStoryboard storyboardWithName:@"Main" bundle:[NSBundle mainBundle]];
    NSViewController *mainMenuVc = [mainsb instantiateControllerWithIdentifier:@"MainMenuViewController"];
    [[mainMenuVc view] setFrame:self.view.frame];
    [[NSApp mainWindow] setContentViewController:mainMenuVc];
}

- (IBAction)goToEditorPage {
    NSStoryboard *mainsb = [NSStoryboard storyboardWithName:@"Main" bundle:[NSBundle mainBundle]];
    NSViewController *createAIVc = [mainsb instantiateControllerWithIdentifier:@"CreateAIViewController"];
    [[createAIVc view] setFrame:self.view.frame]; 
    [[NSApp mainWindow] setContentViewController:createAIVc];
}

@end
