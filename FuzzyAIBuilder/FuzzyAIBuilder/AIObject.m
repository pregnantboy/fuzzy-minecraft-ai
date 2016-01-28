//
//  AIObject.m
//  FuzzyAIBuilder
//
//  Created by Ian Chen on 29/1/16.
//  Copyright © 2016 Ian Chen. All rights reserved.
//

#import "AIObject.h"

@interface AIObject () {
    NSMutableArray *ruleList;
    NSString *name;
}

@end

@implementation AIObject

- (instancetype)initWithAIName:(NSString *)AIName {
    self = [super init];
    if (self) {
        name = AIName;
        ruleList = [[NSMutableArray alloc] init];
    }
    return self;
}

- (void)saveNewRule:(Rule*)newRule {
    [ruleList addObject:newRule];
}

- (Rule *)getRuleAtIndex:(NSInteger) index {
    return [ruleList objectAtIndex:index];
}

- (void)replaceExistingRule:(NSInteger) index withRule: (Rule*)modifiedRule {
    [ruleList replaceObjectAtIndex:index withObject:modifiedRule];
}

- (NSArray *)ruleList {
    return ruleList;
}

- (NSInteger)getNumRules {
    return [ruleList count];
}

- (NSString *)getName {
    return name;
}

@end
