//
//  RuleOutput.h
//  FuzzyAIBuilder
//
//  Created by Ian Chen on 28/1/16.
//  Copyright © 2016 Ian Chen. All rights reserved.
//

#import <Foundation/Foundation.h>

@interface RuleOutput : NSObject

+ (NSDictionary *)actions;
+ (NSArray *)getPossibleActions;
+ (NSArray *)getPossibleModifiersForAction:(NSString *)action;

@end
